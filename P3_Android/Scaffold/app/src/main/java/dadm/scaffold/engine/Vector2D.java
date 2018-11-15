
public class Vector2D {
	private double x,y;
	public Vector2D(){x=0.0d;y=0.0d;}
	public Vector2D(double x,double y){
		this.x=x;
		this.y=y;
	}
	public double cross(Vector2D v){
		return x*v.y-y*v.x;
	}
	public double x(Vector2D v){
		return x*v.y-y*v.x;
	}
	public double dot(Vector2D v){
		return x*v.x+y*v.y;
	}
	public Vector2D sub(Vector2D v){
		return new Vector2D(x-v.x,y-v.y);
	}	
	public Vector2D add(Vector2D v){
		return new Vector2D(x+v.x,y+v.y);
	}	
	public Vector2D add_mul(Vector2D v,double d){
		return new Vector2D(x+v.x*d,y+v.y*d);
	}
	public Vector2D mul(double d){
		return new Vector2D(x*d,y*d);
	}
	public Vector2D div(double d){
		return new Vector2D(x/d,y/d);
	}
	public Vector2D normal(){
		double l=Math.sqrt(x*x+y*y);
		return new Vector2D(x/l,y/l);
	}
	public Vector2D neg(){
		return new Vector2D(-x,-y);
	}
	public Vector2D subi(Vector2D v){
		x-=v.x;
		y-=v.y;
		return this;
	}	
	public Vector2D addi(Vector2D v){
		x+=v.x;
		y+=v.y;
		return this;
	}	
	public Vector2D add_muli(Vector2D v,double d){
		x+=v.x*d;
		y+=v.y*d;
		return this;
	}
	public Vector2D muli(double d){
		x*=d;
		y*=d;
		return this;
	}
	public Vector2D divi(double d){
		x/=d;
		y/=d;
		return this;
	}
	public Vector2D normalize(){
		double l=Math.sqrt(x*x+y*y);
		x/=l;
		y/=l;
		return this;
	}
	public Vector2D negi(){
		x*=-1;
		y*=-1;
		return this;
	}
	public double length2(){return x*x+y*y;}
	public double length() {return Math.sqrt(x*x+y*y);}
	public double getX(){return x;}
	public double getY(){return y;}
	public double getX2(){return x*x;}
	public double getY2(){return y*y;}
	public Vector2D set(double x,double y){
		this.x=x;
		this.y=y;
		return this;
	}
	public Vector2D set(Vector2D v){
		this.x=v.x;
		this.y=v.y;
		return this;
	}
}
