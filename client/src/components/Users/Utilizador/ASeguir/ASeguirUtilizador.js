import React from 'react';
import moment from "moment";
import ApiEventos from '../../../../api/ApiEventos';
import ApiUsers from '../../../../api/ApiUsers';

class ASeguirUtilizador extends React.Component {
    state = {
        eventosASeguir: [{
            nome: '',
            inicio: '',
            fim: '',
            descricao: '',
            responsavel: '',
            espaco: '',
            aSeguir: true
        }]
        , showGoogleCalendar: false,
        googleCode: ''
    };

    async componentDidMount() {

        let eventosASeguirFinal = [];
        const eventosASeguir = await ApiEventos.eventosASeguir();

        console.log(eventosASeguir)
        eventosASeguir.forEach(e => {
            const newE = {
                nome: e.nome,
                inicio: moment(e.dateTimeInicial).format('HH:mm'),
                fim: moment(e.dateTimeFinal).format('HH:mm'),
                data: moment(e.dateTimeInicial).format('DD/MM'),
                responsavel: e.utilizadorResponsavel.nome,
                espaco: e.espaco.designacao
            }
            eventosASeguirFinal.push(newE);
        })

        this.setState({
            eventosASeguir: eventosASeguirFinal
        })

    }

    handleUnfollow = (nomeEvento) => {
        const filtered = this.state.eventosASeguir.filter(evento => evento.nome !== nomeEvento);

        this.setState({
            eventosASeguir: filtered
        });

        // TODO: integrar unfollow do evento
    };


    showGoogleCalendarSection = () => {
        this.setState({
            showGoogleCalendar: !this.state.showGoogleCalendar
        })
    }

    syncWithCalendar = async () => {
        const request = await ApiUsers.calendarPermissions(this.state.googleCode);
        console.log("GOOGLE CALENDAR", request)
    }

    handleChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value
        });
    }

    render() {

        const sectionGoogleCalendar = (
            <div stlye={{ float: 'right' }}>
                <p>Enter your confirmation code, you can find it here <a href="https://accounts.google.com/signin/oauth/oauthchooseaccount?client_id=810326640331-eth52d1a0ocejiero78e6edosfbcf9vf.apps.googleusercontent.com&as=3Q7CLZ83FSMbzAauCf2JjA&approval_state=!ChQ5ZzFDTjJ0SG9PdUpybjh2UmRENhIfWXphYXJvSm5aZlllUUhVU2RlLXNqMHlyek5NUXV4WQ%E2%88%99AJDr988AAAAAXRw2K7nBzl7fU1kaSJ0FNwloDW8-UvtV&oauthriskyscope=1&xsrfsig=ChkAeAh8T7lh5ujJUcH0bT_iLiJjAzuEG02vEg5hcHByb3ZhbF9zdGF0ZRILZGVzdGluYXRpb24SBXNvYWN1Eg9vYXV0aHJpc2t5c2NvcGU&flowName=GeneralOAuthFlow">here.</a></p>
                <input type="text" name="googleCode" value={this.state.googleCode} onChange={(e) => this.handleChange(e)} />
                <button type="button" onClick={() => this.syncWithCalendar()}>Submit</button>
            </div>
        )

        return (
            <div>
                <h4 style={{ float: 'left' }}>Eventos seguidos por ti</h4>


                {
                    this.state.eventosASeguir.length >= 1 &&
                    <table className="table ec-table">
                        <thead>
                            <tr>
                                <th scope="col">Espaço</th>
                                <th scope="col">Nome</th>
                                <th scope="col">Data</th>
                                <th scope="col">Início</th>
                                <th scope="col">Fim</th>
                                <th scope="col">Responsável</th>
                                <th scope="col" />

                            </tr>
                        </thead>
                        <tbody>
                            {this.state.eventosASeguir.map(evento => (
                                <tr>
                                    <td>{evento.espaco}</td>
                                    <td>{evento.nome}</td>
                                    <td>{evento.data}</td>
                                    <td>{evento.inicio}</td>
                                    <td>{evento.fim}</td>
                                    <td>{evento.responsavel}</td>
                                    <td>
                                        <a className="alert-warning" onClick={() => this.handleUnfollow(evento.nome)}>
                                            <i className="material-icons individual-icon pucpdrIconAceite">
                                                star
                                        </i>
                                        </a>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                }
                {
                    this.state.eventosASeguir.length < 1 &&
                    <p style={{ textAlign: 'center' }}>Não se encontra a seguir nenhum evento</p>
                }

                <button className="btn btn-filter" style={{ float: 'right', maxWidth: '300px', color: 'black' }}
                    onClick={() => this.showGoogleCalendarSection()}
                >Sync Google Calendar</button>

                {this.state.showGoogleCalendar && sectionGoogleCalendar}


            </div >
        );
    }
}

export default ASeguirUtilizador;
