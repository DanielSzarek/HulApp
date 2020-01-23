import React from 'react';

class TrackItem extends React.Component{

    constructor(props) {
        super(props);

        this.state = {
            track: props.data,
            timeStart: '',
            timeFinish: '',
            duration: '',
            length: ''
        }
    }

    render() {
        return (
            <div>
                <h4>{this.state.track.time_start}</h4>
                <p>Czas trwania: {this.state.track.duration}</p>
                <p>Odległość: {this.state.track.track_length}</p>
            </div>
        );
    }

}export default TrackItem