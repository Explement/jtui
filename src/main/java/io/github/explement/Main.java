package io.github.explement;

import java.io.IOException;

import io.github.explement.input.InputHandler;
import io.github.explement.input.InputManager;

public class Main {
    public static void main(String[] args) throws InterruptedException, IOException {
        InputManager inputManager = new InputManager(new InputHandler());


        while (true) { // ? Keeps main thread alive
            Thread.sleep(1000);
        }










        /* 
        TerminalFactory terminalFactory = new TerminalFactory();

        Renderer renderer = new Renderer(terminalFactory);

        terminalFactory.setCell(new Cell('B', Style.RED), new Vector2(0, 0));
        terminalFactory.setCell(new Cell('E', Style.YELLOW), new Vector2(3, 4));
        terminalFactory.setCell(new Cell('L', Style.YELLOW), new Vector2(5, 6));
        terminalFactory.setCell(new Cell('L', Style.GREEN), new Vector2(7, 8));
        terminalFactory.setCell(new Cell('O', Style.WHITE), new Vector2(3, 9));

        renderer.render();

        Thread.sleep(4000);

        terminalFactory.setCell(new Cell('W', Style.GREEN), new Vector2(0, 0));
        terminalFactory.setCell(new Cell('O', Style.GREEN), new Vector2(0, 1));
        terminalFactory.setCell(new Cell('R', Style.GREEN), new Vector2( 0, 2));
        terminalFactory.setCell(new Cell('D', Style.GREEN), new Vector2(0, 3));
        terminalFactory.setCell(new Cell('S', Style.GREEN), new Vector2(0, 4));

        renderer.render(); */
    }
}