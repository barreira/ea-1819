import React,
{
    Component
}

    from 'react';
import FullCalendar from '@fullcalendar/react';
import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from '@fullcalendar/timegrid';


// Calendar Stylesheet
import '../../../node_modules/@fullcalendar/core/main.css';
import '../../../node_modules/@fullcalendar/daygrid/main.css';
import '../../../node_modules/@fullcalendar/timegrid/main.css';

import './WeeklyCalendar.css';


class WeeklyCalendar extends Component {
    constructor(props) {
        super(props);

        this.state = {}
    }

    render() {
        return (
            <div>
                <FullCalendar defaultView="timeGridWeek"
                    allDaySlot={false}
                    plugins={[timeGridPlugin]}
                    minTime={'06:00:00'}
                    maxTime={'24:00:00'}
                    events={this.props.events}
                    locale='pt'
                /> </div>);
    }
}

export default WeeklyCalendar;