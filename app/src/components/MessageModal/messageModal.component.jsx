import { useGlobalMessage } from "../../context/message";

import { Button } from "../Button/button.component";
import "./messageModal.css";

export function MessageModal() {
  const [modal, setModal] = useGlobalMessage();

  const modalTitle = modal.type === 'message' ? 'Aviso' : 'OPS... ❌';

  return modal.show ? (
    <div className="screenOverlay">
      <div className={`modal ${modal.type}`}>
        <h1 className="modalTitle">{modalTitle}</h1>
        <p className="modalMsg">{modal.message}</p>
        <Button
          type="button"
          className="contained-white medium"
          onClick={() => setModal({...modal, show: false})}
        >
          OK
        </Button>
      </div>
    </div>
  ) : null;
}
