import { useEffect, useState } from "react";
import { buyItem } from "../../api/STORE/buyItem";
import { useGlobalMessage } from "../../context/message";
import { useLoading } from "../context/useLoading.hook";

export function useTransaction(setFetchTrigger) {
  const [selectedItem, setSelectedItem] = useState();
  const [showModal, setShowModal] = useState(false);

  const { setLoading } = useLoading();
  const [, setModal] = useGlobalMessage();

  const handleBuy = async () => {
    try {
      setLoading(true);
      await buyItem(selectedItem.id);
      setShowModal(false);
      setModal("Compra realizada com sucesso.", "message", true);
      setFetchTrigger(true);
      setSelectedItem();
    } catch (error) {
      setShowModal(false);
      setModal(error.response.data.message, "error", true);
      setSelectedItem();
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (selectedItem) {
      setShowModal(true);
    }
  }, [selectedItem]);

  return { setSelectedItem, selectedItem, showModal, setShowModal, handleBuy };
}
