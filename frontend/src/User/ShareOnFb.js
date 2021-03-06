import React, { Component } from 'react';
import { FacebookProvider, Share } from 'react-facebook';
import FacebookIcon from '@material-ui/icons/Facebook';


export default class Example extends Component {
  render() {
    return (
      <FacebookProvider appId="2645722948997451">
        <Share href="http://localhost:3000/login">
          {({ handleClick, loading }) => (
            <button className="facebook-share-link-button" type="button" disabled={loading} onClick={handleClick}><div className="fb-icon"><FacebookIcon /></div></button>
          )}
        </Share>
      </FacebookProvider>
    );
  }
}

// import Facebook from 'react-sharingbuttons/dist/buttons/Facebook'
// import Twitter from 'react-sharingbuttons/dist/buttons/Twitter'

// const sharingButtons = () => {
//   const url = 'https://github.com/caspg/react-sharingbuttons'
//   const shareText = 'Check this site!'

//   return (
//     <div>
//       <Facebook url={url} />
//       <Twitter url={url} shareText={shareText} />
//     </div>
//   )
// }
