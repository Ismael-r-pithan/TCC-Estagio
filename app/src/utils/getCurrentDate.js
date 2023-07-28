export function getCurrentDate() {
  const now = new Date();
  return now.toISOString().slice(0, 10);
}
