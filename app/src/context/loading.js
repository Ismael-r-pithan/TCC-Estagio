import createGlobalState from "react-create-global-state";

const initialState = false;

const [useGlobalLoading, Provider] = createGlobalState(initialState);

export const GlobalLoadingProvider = Provider;

export { useGlobalLoading };
