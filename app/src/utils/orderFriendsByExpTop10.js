export function orderFriendsByExpTop10(friendList) {
  if (friendList) return friendList.sort((a, b) => b.experienceFriend - a.experienceFriend).slice(0, 10);
}
