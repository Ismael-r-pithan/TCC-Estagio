export function getWeekDayString(date) {
  const weekDays = [
    "Domingo",
    "Segunda-feira",
    "Terça-feira",
    "Quarta-feira",
    "Quinta-feira",
    "Sexta-feira",
    "Sábado",
  ];
  const dayNumber = new Date(date).getDay();
  return weekDays[dayNumber];

}
