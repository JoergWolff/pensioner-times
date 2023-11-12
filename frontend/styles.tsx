import {createGlobalStyle} from "styled-components";

export const GlobalStyle = createGlobalStyle`
  :root {
    --total-border: 1px solid wheat;
    --main-background-color: darkslategrey;
    --main-color: wheat;
    --header-background-color: wheat;
    --header-color: darkslategrey;
    --nav-background-color: wheat;
    --nav-color: darkslategrey;
    --link-background-color: darkslategrey;
    --link-color: wheat;

    h1, h2, h3, h4, h5, h6 {
      text-align: center;
    }
  }

  *,
  *::before,
  *::after {
    box-sizing: border-box;
    padding: 0;
    margin: 0;
  }

  body {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    position: absolute;
    top: 0;
    bottom: 0;
    min-width: 25.7em;
    font-family: system-ui;
    background-color: var(--main-background-color);
    color: var(--main-color);
    border: var(--total-border);
  }

  header {
    display: flex;
    justify-content: center;
    position: absolute;
    top: 0;
    right: 0;
    left: 0;
    background-color: var(--header-background-color);
    color: var(--header-color);
    border: var(--total-border);
  }

  .header_div {
    margin: 0.9em;
    font-size: 1em;
    font-weight: bold;
  }

  nav {
    display: flex;
    justify-content: space-evenly;
    position: absolute;
    right: 0;
    bottom: 0;
    left: 0;
    padding: 0.3em;
    background-color: var(--nav-background-color);
  }

  main {
    display: flex;
    flex-direction: column;
    align-items: center;
    position: absolute;
    top: 3.2em;
    right: 0;
    bottom: 2.5em;
    left: 0;
    overflow: scroll;
    overflow-y: auto;
    overflow-x: hidden;
    border: var(--total-border);
    padding: 1em;
  }

  article {
    padding: 1em;
    border: var(--total-border);
    border-radius: 2em;
    width: 20em;
  }

  form {
    display: flex;
    flex-direction: column;
    margin: 1em;
    padding: 1em;
    border: var(--total-border);
  }

  .form_button_div {
    display: flex;
    justify-content: space-evenly;
  }

  .form_button {
    background-color: wheat;
    color: darkslategray;
    padding: 0.5em;
    width: 6em;
  }

  input {
    margin: 1em;
    padding: 0.5em;
    border: none;
    border-radius: 5em;
  }

  textarea {
    margin: 1em;
    padding: 0.5em;
    border: none;
    overflow: hidden;
    border-radius: 1em;
    text-align: center;
    resize: none;
  }

  fieldset {
    display: flex;
    flex-direction: column;
    border: none;
  }

  button {
    background-color: wheat;
    color: darkslategray;
    padding: 0.4em;
    border: none;
    border-radius: 5em;
    width: 6em;
  }
  
  .div_dash{
    display: flex;
    justify-content: space-between;
    overflow: scroll;
    overflow-y: auto;
    overflow-x: hidden;
    width: 100%;
  }

  .div_home_announcement{
    display: flex;
    flex-direction: column;
    justify-content: center;
    width: 20em;
    text-align: center;
  }
  
  .div_hobby_collector {
    display: flex;
    justify-content: space-between;
    font-size: 0.8em;
    width: 10em;
  }

  p {
    margin: 0.5em;
  }

  a {
    background-color: var(--link-background-color);
    color: var(--link-color);
    text-decoration: none;
    text-align: center;
    padding: 0.3em;
    border-radius: 0.6em;
    min-width: 8em;
  }
  
  .participate{
    background-color: var( --header-background-color);
    color: var(--header-color);
  }
  
  @media screen and (min-width: 600px){
    body{
      min-width: 33em;
      right: 10%;
      left: 10%;
    }
  }
`;