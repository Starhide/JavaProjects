camera {
	perspective
	location <-5, 4, -5>
	look_at <0, 1.5, 0>
}

light_source {
	<-5, 4, -3>,
	color rgb <0.95, 0.95, 0.85>
	spotlight 
	radius 15
	falloff 50
	point_at <0, 1.5, 0>
}

plane {
	<0,1,0>, -1 
	pigment { 
		rgb <0.25, 0.5, 0.125> 
	}
}

difference {
	union {
		box { 
			<1, 2.75, 0.5> , <-1, 2, -0.5> 
			finish { 
				diffuse 0.2 
				reflection { 
					metallic 0.6 
				} 
			} 
			pigment { 
				rgb <0.6, 0.6, 0.6> 
			} 
		}
		
		cylinder { 
			<1, 2.75, 0>, <-1, 2.75, 0>, 0.5 
			pigment { 
				rgb <0.6, 0.6, 0.6> 
			}		
			finish { 
				diffuse 0.2 
				reflection { 
					metallic 0.6
				} 
			} 
		}
	}
	box { 
		<-1, 2.5, -0.375>, <0, 2.75, 0.375> 
	}
}

cylinder {
	<0, -1, 0> , <0, 2, 0> , 0.125  
	pigment { 
		rgb <0.5, 0.25, 0.25>
	}
	finish {
		diffuse 0.6
		reflection { 
			metallic 0.3 
		} 
	} 
}