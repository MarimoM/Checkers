package command;

import java.awt.event.MouseEvent;

public class MouseClickCommand implements Command {
    private Click click;

    public MouseClickCommand(Click click){
        this.click = click;
    }

    @Override
    public void execute(MouseEvent e) {
        click.mouseClicked(e);
    }
}
