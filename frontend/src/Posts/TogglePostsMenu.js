import React, { PureComponent } from 'react';
import MenuItem from '@material-ui/core/MenuItem';
import ToggleMenu from './ToggleMenu.js'



export default class TogglePostsMenu extends PureComponent {

  showRight = () => {
    this.right.show();
  }

  render() {

    return (
      <div>
      <text onClick={this.showRight}>Tablica</text>
      <ToggleMenu ref={right => this.right = right} alignment="right">
      <MenuItem hash="first-page">Tablica postów</MenuItem>
      <MenuItem hash="second-page">Moje posty</MenuItem>
      </ToggleMenu>
      </div>
    );
  }
}