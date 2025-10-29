import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.github.explement.input.InputHandler;
import io.github.explement.input.InputManager;
import io.github.explement.input.Keys;

public class InputLayerTests {
    @Nested
    class InputManagerTests { // TODO: Add more tests (probably for key spamming + multiple keys at once)
        private InputHandler mockHandler;
        private InputManager.User32 mockUser32;
        private InputManager inputManager;
        @BeforeEach
        void setup() {
            mockHandler = Mockito.mock(InputHandler.class);
            mockUser32 = Mockito.mock(InputManager.User32.class);
            inputManager = new InputManager(mockHandler, mockUser32, false);
        }

        @Test
        void keyPressTriggersHandleInput() {
            Keys testKey = Keys.A;

            when(mockUser32.GetAsyncKeyState(testKey.vKey())).thenReturn((short) 0x8000);
            

            inputManager.update();

            verify(mockHandler).handleInput(testKey);
        }

        @Test
        void keyHoldDoesNotRetriggerHandleInput() {
            Keys testKey = Keys.A;

            when(mockUser32.GetAsyncKeyState(testKey.vKey())).thenReturn((short) 0x8000);

            inputManager.update();
            inputManager.update();

            verify(mockHandler, only()).handleInput(testKey);
        }

        @Test
        void keyReleaseAndRepressTriggersHandleInputAgain() {
            Keys testKey = Keys.A;

            when(mockUser32.GetAsyncKeyState(testKey.vKey()))
                .thenReturn((short) 0x8000)
                .thenReturn((short) 0x0000)
                .thenReturn((short) 0x8000); 
            inputManager.update(); 
            inputManager.update(); 
            inputManager.update(); 

            verify(mockHandler, Mockito.times(2)).handleInput(testKey);
        }

    }   
    
    // TODO: Write tests for InputHandler (and maybe Keys?) once actual functionality is implemented
}
