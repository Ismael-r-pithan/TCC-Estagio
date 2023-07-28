import { useEffect, useState } from "react";
import { fetchItems } from "../../api/STORE/listStore";
import { fetchUserItems } from "../../api/STORE/userItems";
import { changeAvatar } from "../../api/USER/changeAvatar";
import { changeBanner } from "../../api/USER/changeBanner";
import { fetchAuthenticatedUserData } from "../../api/USER/me";
import { useGlobalMessage } from "../../context/message";
import { useLoading } from "../context/useLoading.hook";

export function useStore() {
  const [fechTrigger, setFetchTrigger] = useState();
  const [userInventory, setUserInventory] = useState();
  const [storeItems, setStoreItems] = useState();
  const [, setModal] = useGlobalMessage();
  const [userData, setUserData] = useState();
  const { setLoading } = useLoading();
  const [icons, setIcons] = useState();
  const [banners, setBanners] = useState();

  const fetchStoreData = async () => {
    try {
      setLoading(true);
      const data = await fetchItems();
      const res = await fetchUserItems();
      const userDatares = await fetchAuthenticatedUserData();
      setUserInventory(res);
      setStoreItems(data.content);
      setUserData(userDatares)
    } catch (error) {
      setModal(
        "Erro ao carregar loja ou sessão expirada, realize login novamente.",
        "error",
        true
      );
    } finally {
      setLoading(false);
    }
  };

  const handleChangeBanner = async (bannerId) =>{
    try {
      setLoading(true);
      await changeBanner(bannerId)
      setModal("Banner alterado com sucesso.", 'message', true)
    } catch (error) {
      setModal(
        error.response.data.erro,
        "error",
        true
      );
    } finally {
      setLoading(false);
    }
  }

  const handleChangeAvatar = async (avatarId) =>{
    try {
      setLoading(true);
      await changeAvatar(avatarId)
      setModal("Avatar alterado com sucesso.", 'message', true)
    } catch (error) {
      setModal(
        error.response.data.erro,
        "error",
        true
      );
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    if (storeItems) {
      const filteredBanners = storeItems
        .filter((item) => item.type === "CAPA")
        .map((item) => {
          if (userInventory.some(it => it.id === item.id)) return { ...item, owned: true };
          else return item;
        });
        console.log(filteredBanners)
      setBanners(filteredBanners);
      const filteredIcons = storeItems
        .filter((item) => item.type === "AVATAR")
        .map((item) => {
          if (userInventory.some(it => it.id === item.id)) return { ...item, owned: true };
          else return item;
        });
      setIcons(filteredIcons);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [storeItems]);


  useEffect(() => {
    if(fechTrigger){
      fetchStoreData();
      setFetchTrigger(false)
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [fechTrigger])

  useEffect(() => {
    fetchStoreData();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  return { icons, banners, handleChangeAvatar, handleChangeBanner, setFetchTrigger, userData };
}
