package com.gestaoespacos.app.beans;

import com.gestaoespacos.app.model.Evento;
import com.gestaoespacos.app.model.IdNotFoundException;
import com.gestaoespacos.app.model.Utilizador;
import com.gestaoespacos.app.repositories.UserRepository;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

@Service
@Scope(value= ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CalendarBean {

    private HttpTransport httpTransport;
    private JsonFactory jsonFactory;

    private UserRepository ur;

    private String clientId;// = "810326640331-eth52d1a0ocejiero78e6edosfbcf9vf.apps.googleusercontent.com";
    private String clientSecret;// = "kaah5soq6CYm9jI0qbBhfpoj";
    private String redirectUrl;// = "urn:ietf:wg:oauth:2.0:oob";
    private String scope;// = "https://www.googleapis.com/auth/calendar";


    @Autowired
    public CalendarBean(UserRepository ur) {

        this.ur = ur;

        clientId = "810326640331-eth52d1a0ocejiero78e6edosfbcf9vf.apps.googleusercontent.com";
        clientSecret = "kaah5soq6CYm9jI0qbBhfpoj";
        redirectUrl = "urn:ietf:wg:oauth:2.0:oob";
        scope = "https://www.googleapis.com/auth/calendar";

        try {
            this.httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        } catch (Exception e) {
            System.out.println(e);
        }



        this.jsonFactory = JacksonFactory.getDefaultInstance();
    }

    /**
     * Efetua a sincronização do Primary Google Calendar do utilizador, tendo em conta os eventos que está a seguir
     * @param id do utilizador
     * @param code para obter o token com o google
     * @return sucesso na sincronização
     * @throws IdNotFoundException se não existir Utilizador(CPDR) com esse id
     */
    public boolean syncCalendar(long id, String code) throws IdNotFoundException {

        Utilizador u = ur.getOne(id);

        if (u == null)
            throw new IdNotFoundException("Utilizador with id=" + id + " not found.");

        try {

            GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow(
                    httpTransport, jsonFactory, clientId, clientSecret, Collections.singleton(scope)
            );

            GoogleTokenResponse token = flow.newTokenRequest(code).setRedirectUri(redirectUrl).execute();

            Credential credentials = new GoogleCredential.Builder()
                    .setTransport(httpTransport)
                    .setJsonFactory(jsonFactory)
                    .setClientSecrets(clientId, clientSecret)
                    .build()
                    .setFromTokenResponse(token);

            // Initialize Calendar service with valid OAuth credentials
            Calendar service = new Calendar.Builder(httpTransport, jsonFactory, credentials)
                    .setApplicationName("Gestão de Espaços").build();


            //Limpar Calendário atual
            service.calendars().clear("primary").execute();

            //Para cada evento que está a seguir, adicionamos ao calendário
            u.getEventosASeguir().forEach(e -> addEvento(service, e));


            return true;
        } catch (Exception e) {
            System.out.println(e);
        }


        return false;
    }

    /**
     * Adiciona um evento (periódico) ao calendário
     * @param c Calendario
     * @param e Evento
     */
    private void addEvento(Calendar c, Evento e) {
        LocalDateTime inicio = e.getDateTimeInicial();
        LocalDateTime fim = e.getDateTimeFinal();
        int p = e.getPeriodicidade();
        LocalDateTime limite = e.getLimite();


        try{
            c.events().insert("primary", makeEvent(e, inicio, fim)).execute();
            inicio = inicio.plusDays(p);
            fim = fim.plusDays(p);

            if(p > 0)
                while(inicio.isBefore(limite) || inicio.isEqual(limite)){
                    c.events().insert("primary", makeEvent(e, inicio, fim)).execute();
                    inicio = inicio.plusDays(p);
                    fim = fim.plusDays(p);
                }


        }catch(IOException exc){ System.out.println(exc); }

    }

    /**
     * Constrói um evento do tipo Event compatível com Google Calendar.
     * @param e Evento
     * @param inicio do evento
     * @param fim do evento
     * @return Event
     */
    private Event makeEvent(Evento e, LocalDateTime inicio, LocalDateTime fim){
        Event r = new Event();
        r.setSummary(e.getNome());
        r.setDescription(e.getDescricao());
        r.setStart(new EventDateTime().setDateTime(toDateTime(inicio)));
        r.setEnd(new EventDateTime().setDateTime(toDateTime(fim)));

        return r;
    }

    /**
     * Converte LocalDate em DateTime
     * @param dateToConvert
     * @return
     */
    private DateTime toDateTime(LocalDateTime dateToConvert) {
        Date data = java.util.Date
                        .from(dateToConvert.atZone(ZoneId.systemDefault())
                                .toInstant());

        return new DateTime(data, TimeZone.getDefault());
    }

    /**
     * @return Retorna o URL para o utilizador fornecer permissões para editar o seu Google Calendar.
     */
    public String getAuthorizationUrl(){
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow(
                httpTransport, jsonFactory, clientId, clientSecret, Collections.singleton(scope)
        );

        return flow.newAuthorizationUrl().setRedirectUri(redirectUrl).build();
    }

}

            /*OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/JSON");
            RequestBody body = RequestBody.create(mediaType, "{\"username\":\"" + email + "\", " +
                                                                        "\"password\" : \"" + password + "\" }");

            Request request = new Request.Builder()
                    .url("http://localhost:8080/public/users/login")
                    .post(body)
                    .addHeader("content-type", "application/JSON")
                    .build();

            Response response = client.newCall(request).execute();

            Scanner sc = new Scanner(response.body().byteStream());
            while(sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }*/

            /*BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            String code = in.readLine();//"4/dwG3CvN1NrSAl2Ym8WGZ6qn61cQLICC5iBqFRh9kkGstL-Lp7e5yx5U";*/