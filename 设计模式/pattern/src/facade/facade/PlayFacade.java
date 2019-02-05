package facade.facade;

import facade.subsystem.Curtain;
import facade.subsystem.PS4;
import facade.subsystem.Projector;
import facade.subsystem.Sound;

public class PlayFacade {
    private PS4 ps4;
    private Projector projector;
    private Sound sound;
    private Curtain curtain;

    public PlayFacade(PS4 ps4, Projector projector,
                      Sound sound, Curtain curtain) {
        this.ps4 = ps4;
        this.projector = projector;
        this.sound = sound;
        this.curtain = curtain;
    }

    public void dayPlay() {
        ps4.openPS4 ();
        projector.openProjector ();
        sound.openSound ();
        sound.volume (60);
        curtain.closeCurtain ();
    }

    public void nightPlay() {
        ps4.openPS4 ();
        projector.openProjector ();
        sound.openSound ();
        sound.volume (30);
    }

    public void close() {
        curtain.closeCurtain ();
        sound.closeSound ();
        projector.closeProjector ();
        ps4.closePS4 ();
    }
}
