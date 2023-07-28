import React from "react";
import { RouterProvider } from "react-router-dom";
import { GlobalMessageProvider } from "./context/message";
import { router } from "./router/index";
import { MessageModal } from "./components/MessageModal/messageModal.component";
import { GlobalLoadingProvider } from "./context/loading";
import { LoadingAnimation } from "./components/LoadingAnimation/loadingAnimation.component";
import { GlobalUserProvider } from "./context/user";
import { Header } from "./components";
import { GlobalActiveFriendProvider } from "./context/activeChat";

function App() {
  return (
    <GlobalUserProvider>
      <GlobalActiveFriendProvider>
        <GlobalMessageProvider>
          <GlobalLoadingProvider>
            <div className="App">
              <Header />
              <RouterProvider router={router} />
            </div>
            <MessageModal />
            <LoadingAnimation />
          </GlobalLoadingProvider>
        </GlobalMessageProvider>
      </GlobalActiveFriendProvider>
    </GlobalUserProvider>
  );
}

export default App;
