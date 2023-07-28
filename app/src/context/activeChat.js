import createGlobalState from "react-create-global-state";

const USER_KEY = "activeFriend";

const stateInStorage = localStorage.getItem(USER_KEY);
const initialState = stateInStorage ? JSON.parse(stateInStorage) : null;

const [_useGlobalActiveFriend, Provider] = createGlobalState(initialState);

function useGlobalActiveFriend() {
  const [_activeFriend, _setActiveFriend] = _useGlobalActiveFriend();

  function setActiveFriend(friend) {
    _setActiveFriend(friend);
    localStorage.setItem(USER_KEY, JSON.stringify(friend));
  }

  return [_activeFriend, setActiveFriend];
}

export const GlobalActiveFriendProvider = Provider;

export { useGlobalActiveFriend};
