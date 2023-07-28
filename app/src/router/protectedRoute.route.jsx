import { Navigate, useLocation } from "react-router-dom";
import { useGlobalUser } from "../context/user";

export function ProtectedRoute({ children, redirectTo = "/" }) {
  const location = useLocation();
  const [user] = useGlobalUser();

  return user ? (
    children
  ) : (
    <Navigate to={{ pathname: redirectTo, state: { from: location } }} />
  );
}
