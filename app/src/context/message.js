import createGlobalState from "react-create-global-state";
const initialState = {
  message: "",
  type: "",
  show: false,
};

const [_useGlobalMessage, Provider] = createGlobalState(initialState);

export const GlobalMessageProvider = Provider;
function useGlobalMessage() {
  const [_modal, _setModal] = _useGlobalMessage();

  function setModal(message, type, show) {
    _setModal({ message: message, type: type, show: show });
  }

  return [_modal, setModal];
}
export { useGlobalMessage };
