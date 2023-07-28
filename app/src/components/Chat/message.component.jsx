import { useGlobalUser } from '../../context/user'
import './chat.css'
export function Message({content}){

    const [user] = useGlobalUser();

    const containerClass = user.id === content.senderId ? 'senderUser' : 'receiverUser'

    return(
    <div className={`messageContainer ${containerClass}`}>
        <p className="messageSender">{`${content.senderName} enviou :`}</p>
        <p className='messageContent'>{content.msgContent}</p>
    </div>
    )
}