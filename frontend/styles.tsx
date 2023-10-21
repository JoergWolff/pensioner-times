import {createGlobalStyle} from "styled-components";

export const GlobalStyle = createGlobalStyle`
  :root {
    --total-border: 0 solid lime;
    --main-background-color: darkslategrey;
    --main-color: wheat;
    --header-background-color: wheat;
    --header-color: darkslategrey;
    --nav-background-color: wheat;
    --nav-color: darkslategrey;
    --link-background-color: darkslategrey;
    --link-color: wheat;
  }
*,
*::before,
*::after{
  box-sizing: border-box;
  padding: 0;
  margin: 0;
}

body{
  display: flex;
  flex-direction: column;
  align-items: center;
  position: absolute;
  top: 0;
  right: 20%;
  bottom: 0;
  left: 20%;
  max-width: 60%;
  min-width: 30em;
  font-family: system-ui;
  background-color: var(--main-background-color);
  color: var(--main-color);
  border: var(--total-border);
}

header{
  display: flex;
  justify-content: space-evenly;
  position: absolute;
  top: 0;
  right: 0;
  left: 0;
  background-color: var(--header-background-color);
  color: var(--header-color);
  border: var(--total-border);
}

nav{
  display: flex;
  justify-content: space-evenly;
  position: absolute;
  top: 2.5em;
  right: 0;
  left: 0;
  padding: 0.3em;
  background-color: var(--nav-background-color);
  color: var(--nav-color);
  border: var(--total-border);
}

main{
  display: flex;
  flex-direction: column;
  align-items: center;
  position: absolute;
  top: 5em;
  right: 0;
  bottom: 0;
  left: 0;
  overflow: scroll;
  overflow-y: auto;
  overflow-x: hidden;
  border: var(--total-border);
}

article{
  padding: 2em;
  margin: 0.5em;
  border: var(--total-border);
}
footer{
  background-color: var(--nav-background-color);
  color: var(--nav-color);
  border: var(--total-border);
}
a{
  background-color: var(--link-background-color);
  color: var(--link-color);
  text-decoration: none;
  text-align: center;
  padding: 0.3em;
  border-radius: 0.5em;
  width: 5em;
}
`;