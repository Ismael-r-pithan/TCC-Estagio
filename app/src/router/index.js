import { createBrowserRouter } from "react-router-dom";
import { ProtectedRoute } from "./protectedRoute.route";
import { AnonymousRoute } from "./anonymousRoute.route";
import {
  LoginScreen,
  RegisterScreen,
  Dashboard,
  LogoutScreen,
  FriendsScreen,
  ProfileScreen,
  PublicProfile,
  PasswordRecoveryScreen,
  NewPasswordScreen,
  ConfirmEmailScreen,
  ResendEmailScreen,
  StoreScreen,
  QuestScreen,
} from "../screens";
import { TasksScreen } from "../screens/TasksScreen/tasks.screen";

export const router = createBrowserRouter([
  {
    path: "/",
    element: (
      <AnonymousRoute redirectTo="/dashboard">
        <LoginScreen />
      </AnonymousRoute>
    ),
  },
  {
    path: "/register",
    element: (
      <AnonymousRoute redirectTo="/dashboard">
        <RegisterScreen />
      </AnonymousRoute>
    ),
  },
  {
    path: "/dashboard",
    element: (
      <ProtectedRoute redirectTo="/">
        <Dashboard />
      </ProtectedRoute>
    ),
  },
  {
    path: "/tarefas",
    element: (
      <ProtectedRoute redirectTo="/">
        <TasksScreen />
      </ProtectedRoute>
    ),
  },
  {
    path: "/logout",
    element: (
      <ProtectedRoute redirectTo="/">
        <LogoutScreen />
      </ProtectedRoute>
    ),
  },
  {
    path: "/meusAmigos",
    element: (
      <ProtectedRoute redirectTo="/">
        <FriendsScreen />
      </ProtectedRoute>
    ),
  },
  {
    path: "/meuPerfil",
    element: (
      <ProtectedRoute redirectTo="/">
        <ProfileScreen />
      </ProtectedRoute>
    ),
  },
  {
    path: "/perfil/:id",
    element: (
      <ProtectedRoute redirectTo="/">
        <PublicProfile />
      </ProtectedRoute>
    ),
  },
  {
    path: "/recuperarSenha",
    element: (
      <AnonymousRoute redirectTo="/">
        <PasswordRecoveryScreen />
      </AnonymousRoute>
    ),
  },
  {
    path: "/reset-password",
    element: (
      <AnonymousRoute redirectTo="/">
        <NewPasswordScreen />
      </AnonymousRoute>
    ),
  },
  {
    path: "/users/activate-account",
    element: (
      <AnonymousRoute redirectTo="/">
        <ConfirmEmailScreen />
      </AnonymousRoute>
    ),
  },
  {
    path: "/resendEmail",
    element: (
      <AnonymousRoute redirectTo="/">
        <ResendEmailScreen />
      </AnonymousRoute>
    ),
  },
  {
    path: "/store",
    element: (
      <ProtectedRoute redirectTo="/">
        <StoreScreen />
      </ProtectedRoute>
    ),
  },
  {
    path: "missoes",
    element: (
      <ProtectedRoute redirectTo="/">
        <QuestScreen />
      </ProtectedRoute>
    ),
  },
]);
