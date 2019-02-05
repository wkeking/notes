package facade;

import facade.facade.PlayFacade;
import facade.subsystem.Curtain;
import facade.subsystem.PS4;
import facade.subsystem.Projector;
import facade.subsystem.Sound;

public class Client {
    public static void main(String args[]) {
        PS4 ps4 = new PS4 ();
        Projector projector = new Projector ();
        Sound sound = new Sound ();
        Curtain curtain = new Curtain ();
        PlayFacade playFacade = new PlayFacade (ps4, projector, sound, curtain);
        playFacade.dayPlay ();
        playFacade.close ();
    }
}
