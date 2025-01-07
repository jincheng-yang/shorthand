float theta0, theta1;
float R0, R1;

controlPoint r0, r1, c0, c1;
controlPoint[] cps;

PImage img;
float img_x = 0;
float img_y = 0;
float scale = 1;

Table table;

void setup() {
  size(570, 850);
  img = loadImage("synopsis.gif");
  table = loadTable("glyphs.csv", "header");

  pixelDensity(displayDensity());

  r0 = new controlPoint(193, 57, 10);
  r1 = new controlPoint(364, 55, 10);
  c0 = new controlPoint(219, 75, 5);
  c1 = new controlPoint(388, 72, 5);
  cps = new controlPoint[] {r0, r1, c0, c1};
  R0 = dist(c0.x, c0.y, r0.x, r0.y) * displayDensity();
  theta0 = atan2(c0.y - r0.y, c0.x - r0.x);
  R1 = dist(c1.x, c1.y, r1.x, r1.y) * displayDensity();
  theta1 = atan2(c1.y - r1.y, c1.x - r1.x);
}

void draw() {
  background(240, 240, 240, 20);
  image(img, img_x, img_y, 570 * scale, 850 * scale);
  stroke(255, 0, 0);
  strokeWeight(1);
  noFill();
  for (TableRow row: table.rows()) {
    float r0x = row.getFloat("r0_x") * scale + img_x;
    float r0y = row.getFloat("r0_y") * scale + img_y;
    float r0 = row.getFloat("r0") * scale;
    float theta0 = row.getFloat("theta0");
    float r1x = row.getFloat("r1_x") * scale + img_x;
    float r1y = row.getFloat("r1_y") * scale + img_y;
    float r1 = row.getFloat("r1") * scale;
    float theta1 = row.getFloat("theta1");
    bezier(r0x, r0y, r0x + r0 * sin(theta0), r0y - r0 * cos(theta0), r1x - r1 * sin(theta1), r1y + r1 * cos(theta1), r1x, r1y);
    circle(r0x, r0y, 5);
  }
  
  if (mousePressed) {
    for (controlPoint cp: cps) {
      cp.pressed();
    }
    
    if (r0.activated) {
      c0.x = c0.x_old + r0.x - r0.x_old;
      c0.y = c0.y_old + r0.y - r0.y_old;
    } 
    if (r1.activated) {
      c1.x = c1.x_old + r1.x - r1.x_old;
      c1.y = c1.y_old + r1.y - r1.y_old;
    } 
    if (c0.activated) {
      R0 = dist(c0.x, c0.y, r0.x, r0.y) * displayDensity();
      theta0 = atan2(c0.y - r0.y, c0.x - r0.x);
    } 
    if (c1.activated) {
      R1 = dist(c1.x, c1.y, r1.x, r1.y) * displayDensity();
      theta1 = atan2(c1.y - r1.y, c1.x - r1.x);
    }
  } 
  
  strokeWeight(1);
  stroke(137, 67, 140, 50);
  fill(255, 0);
  circle(c0.x, c0.y, R0);
  circle(c1.x, c1.y, R1);
  line(c0.x, c0.y, r0.x, r0.y);
  line(c1.x, c1.y, r1.x, r1.y);
  strokeWeight(5);
  stroke(255, 0, 0);
  bezier(r0.x, r0.y, r0.x + R0 * sin(theta0), r0.y - R0 * cos(theta0), r1.x - R1 * sin(theta1), r1.y + R1 * cos(theta1), r1.x, r1.y);
  
  for (controlPoint cp: cps) {
    cp.show();
  }
  
}

void mouseReleased() {
  for (controlPoint cp: cps) {
    cp.released();
  }
}

void keyPressed() {
   if (keyCode == UP) {
       img_y += 5;
   }
   if (keyCode == DOWN) {
       img_y -= 5;
   }
   if (keyCode == LEFT) {
       img_x += 5;
   }
   if (keyCode == RIGHT) {
       img_x -= 5;
   }
   if (key == '-') {
       scale *= .9;
   } 
   if (key == '=') {
       scale /= .9;
   }
   if (key == 'p') {
       float[] numbers = new float[]{img_x, img_y, scale, r0.x, r0.y, c0.x, c0.y, R0, theta0, r1.x, r1.y, c1.x, c1.y, R1, theta1};
       for (float number : numbers) {
            print(number + ", ");
        }
   }
}
