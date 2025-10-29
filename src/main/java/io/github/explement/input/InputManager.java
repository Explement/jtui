package io.github.explement.input;

import java.util.HashMap;

import com.sun.jna.Library;
import com.sun.jna.Native;

public class InputManager {
   private final InputHandler inputHandler;
   private final HashMap<Keys, Boolean> keyStates = new HashMap<>();
   private final User32 user32;

   public InputManager(InputHandler inputHandler) {
      this(inputHandler, User32.INSTANCE, true);
   }

   public InputManager(InputHandler inputHandler, User32 user32, boolean startInputThread) {
      this.inputHandler = inputHandler;
      this.user32 = user32;
      initializeKeyStates();
      if (startInputThread) createInputThread();
   }

   private void createInputThread() {

      Thread inputThread = new Thread(() -> {
         while (true) {
            update();
            try {
               Thread.sleep(50);
            } catch (InterruptedException e) {
               e.printStackTrace();
            }
         }
      });
      inputThread.setDaemon(true);
      inputThread.start();
   }

   public interface User32 extends Library {
      User32 INSTANCE = Native.load("user32", User32.class);
      short GetAsyncKeyState(int vKey);
   }

   public void update() {
      for (Keys key : Keys.values()) {
        int vKey = key.vKey();
        
        boolean pressed = isKeyPressed(vKey);
        boolean wasPressed = keyStates.get(key);

        if (pressed && !wasPressed) {
            inputHandler.handleInput(key);
        }

        keyStates.put(key, pressed);
      }
   }
   
   public boolean isKeyPressed(int vKey) {
      return (user32.GetAsyncKeyState(vKey) & 0x8000) != 0;
   }

   public void initializeKeyStates() {
      for (Keys key : Keys.values()) {
         keyStates.put(key, false);
      }
   }
}
