// // import React from 'react'
// // import { Button, Comment, Form, Header } from 'semantic-ui-react'

// // const Comments = () => (
// //   <Comment.Group>
// //     <Header as='h3' dividing>
// //       Comments
// //     </Header>

// //     <Comment>
// //       <Comment.Avatar src='https://react.semantic-ui.com/images/avatar/small/matt.jpg' />
// //       <Comment.Content>
// //         <Comment.Author as='a'>Matt</Comment.Author>
// //         <Comment.Metadata>
// //           <div>Today at 5:42PM</div>
// //         </Comment.Metadata>
// //         <Comment.Text>How artistic!</Comment.Text>
// //         <Comment.Actions>
// //           <Comment.Action>Reply</Comment.Action>
// //         </Comment.Actions>
// //       </Comment.Content>
// //     </Comment>

// //     <Comment>
// //       <Comment.Avatar src='https://react.semantic-ui.com/images/avatar/small/elliot.jpg' />
// //       <Comment.Content>
// //         <Comment.Author as='a'>Elliot Fu</Comment.Author>
// //         <Comment.Metadata>
// //           <div>Yesterday at 12:30AM</div>
// //         </Comment.Metadata>
// //         <Comment.Text>
// //           <p>This has been very useful for my research. Thanks as well!</p>
// //         </Comment.Text>
// //         <Comment.Actions>
// //           <Comment.Action>Reply</Comment.Action>
// //         </Comment.Actions>
// //       </Comment.Content>
// //       <Comment.Group>
// //         <Comment>
// //           <Comment.Avatar src='https://react.semantic-ui.com/images/avatar/small/jenny.jpg' />
// //           <Comment.Content>
// //             <Comment.Author as='a'>Jenny Hess</Comment.Author>
// //             <Comment.Metadata>
// //               <div>Just now</div>
// //             </Comment.Metadata>
// //             <Comment.Text>Elliot you are always so right :)</Comment.Text>
// //             <Comment.Actions>
// //               <Comment.Action>Reply</Comment.Action>
// //             </Comment.Actions>
// //           </Comment.Content>
// //         </Comment>
// //       </Comment.Group>
// //     </Comment>

// //     <Comment>
// //       <Comment.Avatar src='https://react.semantic-ui.com/images/avatar/small/joe.jpg' />
// //       <Comment.Content>
// //         <Comment.Author as='a'>Joe Henderson</Comment.Author>
// //         <Comment.Metadata>
// //           <div>5 days ago</div>
// //         </Comment.Metadata>
// //         <Comment.Text>Dude, this is awesome. Thanks so much</Comment.Text>
// //         <Comment.Actions>
// //           <Comment.Action>Reply</Comment.Action>
// //         </Comment.Actions>
// //       </Comment.Content>
// //     </Comment>

// //     <Form reply>
// //       <Form.TextArea />
// //       <Button content='Add Reply' labelPosition='left' icon='edit' primary />
// //     </Form>
// //   </Comment.Group>
// // )

// // export default Comments


// import React from "react";
// import ReactDOM from "react-dom";

// import { Divider, Avatar, Grid, Paper } from "@material-ui/core";

// // import "./styles.css";

// const imgLink =
//   "https://images.pexels.com/photos/1681010/pexels-photo-1681010.jpeg?auto=compress&cs=tinysrgb&dpr=3&h=750&w=1260";

// export default class Comments extends React.Component {
//   render(){
//   return (
//     <div style={{ padding: 14 }} className="App">
//       <h1>Comments</h1>
//       <Paper style={{ padding: "40px 20px" }}>
//         <Grid container wrap="nowrap" spacing={2}>
//           <Grid item>
//             <Avatar alt="Remy Sharp" src={imgLink} />
//           </Grid>
//           <Grid justifyContent="left" item xs zeroMinWidth>
//             <h4 style={{ margin: 0, textAlign: "left" }}>Michel Michel</h4>
//             <p style={{ textAlign: "left" }}>
//               Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean
//               luctus ut est sed faucibus. Duis bibendum ac ex vehicula laoreet.
//               Suspendisse congue vulputate lobortis. Pellentesque at interdum
//               tortor. Quisque arcu quam, malesuada vel mauris et, posuere
//               sagittis ipsum. Aliquam ultricies a ligula nec faucibus. In elit
//               metus, efficitur lobortis nisi quis, molestie porttitor metus.
//               Pellentesque et neque risus. Aliquam vulputate, mauris vitae
//               tincidunt interdum, mauris mi vehicula urna, nec feugiat quam
//               lectus vitae ex.{" "}
//             </p>
//             <p style={{ textAlign: "left", color: "gray" }}>
//               posted 1 minute ago
//             </p>
//           </Grid>
//         </Grid>
//         <Divider variant="fullWidth" style={{ margin: "30px 0" }} />
//         <Grid container wrap="nowrap" spacing={2}>
//           <Grid item>
//             <Avatar alt="Remy Sharp" src={imgLink} />
//           </Grid>
//           <Grid justifyContent="left" item xs zeroMinWidth>
//             <h4 style={{ margin: 0, textAlign: "left" }}>Michel Michel</h4>
//             <p style={{ textAlign: "left" }}>
//               Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean
//               luctus ut est sed faucibus. Duis bibendum ac ex vehicula laoreet.
//               Suspendisse congue vulputate lobortis. Pellentesque at interdum
//               tortor. Quisque arcu quam, malesuada vel mauris et, posuere
//               sagittis ipsum. Aliquam ultricies a ligula nec faucibus. In elit
//               metus, efficitur lobortis nisi quis, molestie porttitor metus.
//               Pellentesque et neque risus. Aliquam vulputate, mauris vitae
//               tincidunt interdum, mauris mi vehicula urna, nec feugiat quam
//               lectus vitae ex.{" "}
//             </p>
//             <p style={{ textAlign: "left", color: "gray" }}>
//               posted 1 minute ago
//             </p>
//           </Grid>
//         </Grid>
//       </Paper>

//       <Paper style={{ padding: "40px 20px", marginTop: 100 }}>
//         <Grid container wrap="nowrap" spacing={2}>
//           <Grid item>
//             <Avatar alt="Remy Sharp" src={imgLink} />
//           </Grid>
//           <Grid justifyContent="left" item xs zeroMinWidth>
//             <h4 style={{ margin: 0, textAlign: "left" }}>Michel Michel</h4>
//             <p style={{ textAlign: "left" }}>
//               Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean
//               luctus ut est sed faucibus. Duis bibendum ac ex vehicula laoreet.
//               Suspendisse congue vulputate lobortis. Pellentesque at interdum
//               tortor. Quisque arcu quam, malesuada vel mauris et, posuere
//               sagittis ipsum. Aliquam ultricies a ligula nec faucibus. In elit
//               metus, efficitur lobortis nisi quis, molestie porttitor metus.
//               Pellentesque et neque risus. Aliquam vulputate, mauris vitae
//               tincidunt interdum, mauris mi vehicula urna, nec feugiat quam
//               lectus vitae ex.{" "}
//             </p>
//             <p style={{ textAlign: "left", color: "gray" }}>
//               posted 1 minute ago
//             </p>
//           </Grid>
//         </Grid>
//       </Paper>
//       <Paper style={{ padding: "40px 20px", marginTop: 10 }}>
//         <Grid container wrap="nowrap" spacing={2}>
//           <Grid item>
//             <Avatar alt="Remy Sharp" src={imgLink} />
//           </Grid>
//           <Grid justifyContent="left" item xs zeroMinWidth>
//             <h4 style={{ margin: 0, textAlign: "left" }}>Michel Michel</h4>
//             <p style={{ textAlign: "left" }}>
//               Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean
//               luctus ut est sed faucibus. Duis bibendum ac ex vehicula laoreet.
//               Suspendisse congue vulputate lobortis. Pellentesque at interdum
//               tortor. Quisque arcu quam, malesuada vel mauris et, posuere
//               sagittis ipsum. Aliquam ultricies a ligula nec faucibus. In elit
//               metus, efficitur lobortis nisi quis, molestie porttitor metus.
//               Pellentesque et neque risus. Aliquam vulputate, mauris vitae
//               tincidunt interdum, mauris mi vehicula urna, nec feugiat quam
//               lectus vitae ex.{" "}
//             </p>
//             <p style={{ textAlign: "left", color: "gray" }}>
//               posted 1 minute ago
//             </p>
//           </Grid>
//         </Grid>
//       </Paper>
//       <Paper style={{ padding: "40px 20px", marginTop: 10 }}>
//         <Grid container wrap="nowrap" spacing={2}>
//           <Grid item>
//             <Avatar alt="Remy Sharp" src={imgLink} />
//           </Grid>
//           <Grid justifyContent="left" item xs zeroMinWidth>
//             <h4 style={{ margin: 0, textAlign: "left" }}>Michel Michel</h4>
//             <p style={{ textAlign: "left" }}>
//               Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean
//               luctus ut est sed faucibus. Duis bibendum ac ex vehicula laoreet.
//               Suspendisse congue vulputate lobortis. Pellentesque at interdum
//               tortor. Quisque arcu quam, malesuada vel mauris et, posuere
//               sagittis ipsum. Aliquam ultricies a ligula nec faucibus. In elit
//               metus, efficitur lobortis nisi quis, molestie porttitor metus.
//               Pellentesque et neque risus. Aliquam vulputate, mauris vitae
//               tincidunt interdum, mauris mi vehicula urna, nec feugiat quam
//               lectus vitae ex.{" "}
//             </p>
//             <p style={{ textAlign: "left", color: "gray" }}>
//               posted 1 minute ago
//             </p>
//           </Grid>
//         </Grid>
//       </Paper>
//       <Paper style={{ padding: "40px 20px", marginTop: 10 }}>
//         <Grid container wrap="nowrap" spacing={2}>
//           <Grid item>
//             <Avatar alt="Remy Sharp" src={imgLink} />
//           </Grid>
//           <Grid justifyContent="left" item xs zeroMinWidth>
//             <h4 style={{ margin: 0, textAlign: "left" }}>Michel Michel</h4>
//             <p style={{ textAlign: "left" }}>
//               Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean
//               luctus ut est sed faucibus. Duis bibendum ac ex vehicula laoreet.
//               Suspendisse congue vulputate lobortis. Pellentesque at interdum
//               tortor. Quisque arcu quam, malesuada vel mauris et, posuere
//               sagittis ipsum. Aliquam ultricies a ligula nec faucibus. In elit
//               metus, efficitur lobortis nisi quis, molestie porttitor metus.
//               Pellentesque et neque risus. Aliquam vulputate, mauris vitae
//               tincidunt interdum, mauris mi vehicula urna, nec feugiat quam
//               lectus vitae ex.{" "}
//             </p>
//             <p style={{ textAlign: "left", color: "gray" }}>
//               posted 1 minute ago
//             </p>
//           </Grid>
//         </Grid>
//       </Paper>
//     </div>
//   );
//   }
// }

