import React from 'react';

class TrackItem extends React.Component{

    constructor(props) {
        super(props);

        this.state = {
            track: props.data,
            auth: true
        }
    }

    formatDateTime(dateString) {
        const date = new Date(dateString);
        return date.toLocaleDateString() + " " + date.toLocaleTimeString();
    }

    render() {
        return (
            <div>
                <h4>{this.formatDateTime(this.state.track.time_start)}</h4>
                <p>Czas trwania: <b>{this.state.track.duration}</b></p>
                <p>Odległość: <b>{this.state.track.track_length}</b> km</p>
            </div>
        );
    }

}export default TrackItem