
public class NBody{
	public static double readRadius(String filename){
		In in=new In(filename);
		in.readInt();
		return in.readDouble();
	}

	public static Planet[] readPlanets(String filename){
		In in=new In(filename);
		int N=in.readInt();
		double R=in.readDouble();
		Planet[] ps=new Planet[N];
		for(int i=0;i<N;i++){
			double xxPos=in.readDouble();
			double yyPos=in.readDouble();
			double xxVel=in.readDouble();
			double yyVel=in.readDouble();
			double mass=in.readDouble();
			String img=in.readString();
			
			ps[i]=new Planet(xxPos,yyPos,xxVel,yyVel,mass,img);

		}
		return ps;
	}

	public static void main(String[] args){
		StdDraw.enableDoubleBuffering();

		double T=Double.parseDouble(args[0]);
		double dt=Double.parseDouble(args[1]);
		String filename=args[2];

		In in=new In(filename);
		int N=in.readInt();
		double R=in.readDouble();

		StdDraw.setScale(-R,R);
		StdDraw.picture(0,0,"images/starfield.jpg");

		Planet[] ps=readPlanets(filename);
		for(Planet p:ps){
			p.draw();
		}

		double time=0.0;
		
		while(time<T){
			double[] xForces=new double[ps.length];
			double[] yForces=new double[ps.length];

			int i=0;
			for(Planet p:ps){
				xForces[i]=p.calcNetForceExertedByX(ps);
				yForces[i]=p.calcNetForceExertedByY(ps);
				i+=1;
			}

			int k=0;
			for (Planet p:ps){
				p.update(dt,xForces[k],yForces[k]);
				k+=1;
			}

			StdDraw.picture(0,0,"images/starfield.jpg");
			for(Planet p:ps){
				p.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);

			time+=dt;
		}

		StdOut.printf("%d\n", ps.length);
		StdOut.printf("%.2e\n", R);
		for (int i = 0; i < ps.length; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  ps[i].xxPos, ps[i].yyPos, ps[i].xxVel,
                  ps[i].yyVel, ps[i].mass, ps[i].imgFileName);   
		}
	}
}