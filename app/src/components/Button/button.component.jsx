import React from "react";
import "./button.css";

export function Button({ className, onClick, type, children }) {
  return (
    <button className={`Button ${className}`} type={type} onClick={onClick}>
      {children}
    </button>
  );
}
