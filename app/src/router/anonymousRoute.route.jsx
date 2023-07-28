import { Navigate, useLocation } from "react-router-dom";
import { useGlobalUser } from "../context/user";

export function AnonymousRoute({ children, redirectTo = "/" }) {
  const [user] = useGlobalUser();
  const location = useLocation();

  return !user ? (
    children
  ) : (
    <Navigate to={{ pathname: redirectTo, state: { from: location } }} />
  );
}
