import { useGlobalLoading } from "../../context/loading";

export function useLoading() {
  const [loading, setLoading] = useGlobalLoading();

  return { loading, setLoading };
}
