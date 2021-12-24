public class NBody{
    private static int n;
    private static double radius;
    private static In in;
    public static double readRadius(String s){
        in = new In(s);
        n = in.readInt();
        radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets(String s){
        readRadius(s);
        Planet[] planets = new Planet[n];
        for(int i=0;i<n;++i){
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, img);
        }
        return planets;
    }

    public static void main(String[] args){
        double T = Double.parseDouble(args[0]), dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] planets = readPlanets(filename);
        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius,radius);

        for(double t=0;T-t>1e-5;t+=dt){
            StdDraw.clear();
            StdDraw.picture(0, 0, "images/starfield.jpg");
            double[] xForces=new double[n],yForces = new double[n];
            for(int i=0;i<n;++i){
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            for(int i=0;i<n;++i){
                planets[i].update(dt,xForces[i], yForces[i]);
            }
            for(Planet p:planets){
                p.draw();
            }
            /* Shows the drawing to the screen. */
		    StdDraw.show();
            StdDraw.pause(10);
        }
    }
}