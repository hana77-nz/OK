package controller;

import models.App;
import models.Result;
import models.enums.AppMenuCommands;

public class MainMenuController {
    public Result ChangeMenu(AppMenuCommands menu) {
        if(menu == AppMenuCommands.UserMenu && App.getLoggedInUser() == null)
            return new Result(false, "You need to login as a user before accessing the user menu.");
        if(menu == AppMenuCommands.StoreMenu && App.getLoggedInStore() == null)
            return new Result(false, "You should login as store before accessing the store menu.");
        return new Result(true, "");
    }
}
