import java.lang.Math;

public class Planet{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	private static final double G=6.67*Math.pow(10,-11);

	public Planet(double xP,double yP,double xV,double yV,double m,String img){
		xxPos=xP;
		yyPos=yP;
		xxVel=xV;
		yyVel=yV;
		mass=m;
		imgFileName=img;
	}

	public Planet(Planet p){
		xxPos=p.xxPos;
		yyPos=p.yyPos;
		xxVel=p.xxVel;
		yyVel=p.yyVel;
		mass=p.mass;
		imgFileName=p.imgFileName;
	}

	public double calcDistance(Planet p){
		double distance=Math.sqrt(Math.pow(p.xxPos-xxPos,2)+Math.pow(p.yyPos-yyPos,2));
		return distance;
	}

	public double calcForceExertedBy(Planet p){
		double F=G*mass*p.mass/Math.pow(calcDistance(p),2);
		return F;
	}

	public double calcForceExertedByX(Planet p){
			return calcForceExertedBy(p)*(p.xxPos-xxPos)/calcDistance(p);
	}

	public double calcForceExertedByY(Planet p){
			return calcForceExertedBy(p)*(p.yyPos-yyPos)/calcDistance(p);
	}

	public double calcNetForceExertedByX(Planet[] ps){
		double netForceX=0;
		for (Planet p:ps){
			if (this.equals(p)){
				continue;
			} else{
				netForceX+=calcForceExertedByX(p);
			}
		}
		return netForceX;
	}

	public double calcNetForceExertedByY(Planet[] ps){
		double netForceY=0;
		for (Planet p:ps){
			if (this.equals(p)){
				continue;
			} else{
				netForceY+=calcForceExertedByY(p);
			}
		}
		return netForceY;
	}


	public void update(double dt,double fX,double fY){
		double aX=fX/mass;
		double aY=fY/mass;
		xxVel+=dt*aX;
		yyVel+=dt*aY;
		xxPos+=dt*xxVel;
		yyPos+=dt*yyVel;
	}

	public void draw(){
		StdDraw.picture(xxPos,yyPos,"images/"+imgFileName);
	}

}