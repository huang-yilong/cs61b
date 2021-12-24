public class Planet {
    public double xxPos;
    public double yyPos;
    public double xxVel;
    public double yyVel;
    public double mass;
    public String imgFileName;
    private static double G=6.67e-11f;

    public Planet(double xP, double yP, double xV, double yV, double m, String img){
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    public Planet(Planet b){
        xxPos = b.xxPos;
        yyPos = b.yyPos;
        xxVel = b.xxVel;
        yyVel = b.yyVel;
        mass = b.mass;
        imgFileName = b.imgFileName;
    }

    public double calcDistance(Planet b){
        return Math.sqrt(Math.pow(xxPos-b.xxPos, 2)+Math.pow(yyPos-b.yyPos, 2));
    }

    public double calcForceExertedBy(Planet b){
        return G*mass*b.mass/Math.pow(calcDistance(b), 2);
    }

    public double calcForceExertedByX(Planet b){
        return calcForceExertedBy(b)*(b.xxPos-xxPos)/calcDistance(b);
    }

    public double calcForceExertedByY(Planet b){
        return calcForceExertedBy(b)*(b.yyPos-yyPos)/calcDistance(b);
    }

    public double calcNetForceExertedByX(Planet[] planets){
        double force = 0f;
        for(Planet b:planets){
            if (!equals(b))
                force += calcForceExertedByX(b);
        }
        return force;
    }

    public double calcNetForceExertedByY(Planet[] planets){
        double force = 0f;
        for(Planet b:planets){
            if (!equals(b))
                force += calcForceExertedByY(b);
        }
        return force;
    }

    public void update(double dt, double fX, double fY){
        double ax = fX / mass;
        double ay = fY / mass;
        xxVel += dt * ax;
        yyVel += dt * ay;
        xxPos += dt * xxVel;
        yyPos += dt * yyVel;
    }


    public void draw(){
		StdDraw.picture(xxPos, yyPos, imgFileName);
    }
}