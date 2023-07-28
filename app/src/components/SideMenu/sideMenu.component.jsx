import "./sideMenu.css";
export function SideMenu({ toggle }) {
  const MENU_LIST = [
    {
      menuName: "DASHBOARD",
      path: "/dashboard",
    },
    {
      menuName: "TAREFAS",
      path: "/tarefas",
    },
    {
      menuName: "PERFIL",
      path: "/meuPerfil",
    },
    {
      menuName: "AMIGOS",
      path: "/meusAmigos",
    },
    {
      menuName: "MISSÕES",
      path: "/missoes",
    },
    {
      menuName: "LOJA",
      path: "/store",
    },
    {
      menuName: "SAIR",
      path: "/logout",
    },
  ];

  return toggle ? (
    <div className="sideMenuContainer">
      <h2 className="sideMenuTitle">MENU</h2>
      {MENU_LIST.map((item, index) => {
        return (
          <a key={index} href={item.path}>
            {item.menuName}
          </a>
        );
      })}
    </div>
  ) : null;
}
