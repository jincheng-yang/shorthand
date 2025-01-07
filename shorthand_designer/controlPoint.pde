class controlPoint {
  float x_old, x;
  float y_old, y;
  float r;
  boolean activated;
  
  controlPoint(float x, float y, float r) {
    this.x = this.x_old = x;
    this.y = this.y_old = y;
    this.r = r;
    this.activated = false;
  }
  
  void pressed() {
    if (this.activated == false) {
      if (dist(mouseX, mouseY, this.x_old, this.y_old) * displayDensity() < this.r) {
        this.activated = true;
      }
    } else {
      this.x = mouseX;
      this.y = mouseY;
    }
  }
  
  void released() {
    this.x_old = this.x;
    this.y_old = this.y;
    this.activated = false;
  }
  
  void show() {
    stroke(0, 0, 0, 80);
    fill(255, 255, 255, 80);
    circle(this.x_old, this.y_old, this.r);
    
    stroke(0);
    fill(255);
    circle(this.x, this.y, this.r);
  }
}
