package javaConfig装配bean;

public class CDPlayer implements MediaPlayer {
  private CompactDisc cd;


  public CDPlayer(CompactDisc cd) {
    this.cd = cd;
  }

  public void play() {
    cd.play();
  }

}
