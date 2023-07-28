import React from "react";
import "./homeScreen.css";
import { Header, LoginForm } from "../../components/index.js";
export function HomeScreen() {
  return (
    <div className="homeScreenContainer screenContainer">
      <Header />
      <h1>Bem vindo ao HouseHero!</h1>
      <LoginForm />
    </div>
  );
}
