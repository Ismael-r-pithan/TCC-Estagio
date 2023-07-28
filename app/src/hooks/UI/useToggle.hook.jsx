import { useState } from "react";

export function useToggle() {
  const [toggle, setToggle] = useState(false);

  function handleClick() {
    setToggle(!toggle);
  }


  return { toggle, handleClick };
}
