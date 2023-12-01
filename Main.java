import java.util.ArrayList;
import processing.core.PApplet;
public class Main extends PApplet {
 float easing = 0.05F;
 float x;
 float y;
 float sunRadius = 50;
 ArrayList<Planet> planets = new ArrayList<>();
 ArrayList<Star> stars = new ArrayList<>();
 int totalPts = 300;
 float steps = totalPts + 1;
 ArrayList<Bomb> bombs = new ArrayList<>(); // New: ArrayList to store bombs
 public void settings() {
 size(900, 900);
 }
 public void setup() {
 noStroke();
 // Membuat bintang
 for (int i = 0; i < 200; i++) {
 stars.add(new Star(random(width), random(height)));
 }
 // Membuat planet
 for (int i = 0; i < 8; i++) {
 float distance = 100 + i * 50;
 float angle = random(TWO_PI);
 float planetRadius = 10 + i * 5;
 float speed = map(distance, 100F, 500F, 0.02F, (float) 0.002F);
 int color;
 if (i == 0) {
 color = color(158, 148, 135);
 } else if (i == 1) {
 color = color(146, 110, 68);
 } else if (i == 2) {
 color = color(128, 165, 217);
 } else if (i == 3) {
 color = color(167, 83, 61);
 } else if (i == 4) {
 color = color(191, 144, 89);
 } else if (i == 5) {
 color = color(229, 183, 71);
 } else if (i == 6) {
 color = color(157, 218, 234);
 } else {
 color = color(74, 98, 211);
 }
 planets.add(new Planet(distance, angle, planetRadius, speed, color));
 }
 }
 public void draw() {
 drawSmoothGradientBackground();
 drawStars();
 translate(width / 2, height / 2);
 // matahari
 fill(255, 255, 0);
 ellipse(0, 0, sunRadius * 2, sunRadius * 2);
 for (Planet planet : planets) {
 planet.move();
 planet.updateTrail();
 planet.display();
 }
 // UFO
 float targetX = mouseX - width / 2;
 float dx = targetX - x;
 x += dx * easing;
 float targetY = mouseY - height / 2;
 float dy = targetY - y;
 y += dy * easing;
 fill(204);
 ellipse(x, y, 60, 20);
 ellipse(x, y - 10, 30, 20);
 fill(255, 0, 0);
 ellipse(x, y - 10, 10, 10);
 line(x, y - 20, x, y - 30);
 for (Bomb bomb : bombs) {
 bomb.move();
 bomb.display();
 }
 }
 void drawSmoothGradientBackground() {
 noStroke();
 for (int i = 0; i <= 450; i += 2) {
 float gradient = map(i, 0, 450, 30, 150);
 fill(0, 0, gradient, 200);
 rect(0, i, 900, 5);
 }
 for (int i = 450; i <= 900; i += 2) {
 float gradient = map(i, 450, 900, 150, 30);
 fill(0, 0, gradient, 200);
 rect(0, i, 900, 5);
 }
 }
 void drawStars() {
 fill(255);
 for (Star star : stars) {
 star.move();
 star.display();
 }
 }
 // UFO Bomb launcher
 public void mousePressed() {
 bombs.add(new Bomb(x, y));
 }
 class Planet {
 float distance;
 float angle;
 float radius;
 float speed;
 int color;
 ArrayList<Float> trailX = new ArrayList<>();
 ArrayList<Float> trailY = new ArrayList<>();
 Planet(float distance, float initialAngle, float radius, float speed, int 
color) {
 this.distance = distance;
 this.angle = initialAngle;
 this.radius = radius;
 this.speed = speed;
 this.color = color;
 }
 void move() {
 angle += speed;
 if (angle > TWO_PI) {
 angle -= TWO_PI;
 }
 }
 // Ekor lintasan
 void updateTrail() {
 float planetX = distance * cos(angle);
 float planetY = distance * sin(angle);
 trailX.add(planetX);
 trailY.add(planetY);
 if (trailX.size() > totalPts) {
 trailX.remove(0);
 trailY.remove(0);
 }
 }
 void display() {
 float planetX = distance * cos(angle);
 float planetY = distance * sin(angle);
 fill(color);
 ellipse(planetX, planetY, radius * 2, radius * 2);
 noFill();
 stroke(color, 150);
 strokeWeight(2);
 beginShape();
 for (int i = 0; i < trailX.size(); i++) {
 vertex(trailX.get(i), trailY.get(i));
 }
 endShape();
 }
 }
 class Star {
 float x;
 float y;
 float speed;
 Star(float x, float y) {
 this.x = x;
 this.y = y;
 this.speed = random(1, 3);
 }
 void move() {
 y += speed;
 if (y > height) {
 y = 0;
 x = random(width);
 }
 }
 void display() {
 ellipse(x, y, 2, 2);
 }
 }
 // UFO bomb
 class Bomb {
 float x;
 float y;
 float speed = 5;
 Bomb(float x, float y) {
 this.x = x;
 this.y = y;
 }
 void move() {
 y -= speed;
 }
 void display() {
 noStroke();
 fill(255, 0, 0);
 ellipse(x, y, 2, 35);
 }
 }
 public static void main(String[] args) {
 String title = "Solar System Simulation";
 String[] processingArgs = {title};
 PApplet.runSketch(processingArgs, new Main());
 }
