#include "colors.inc"

global_settings {
    noise_generator 3
    max_trace_level 4
}        

camera { 
   location  <179.64469174, 15, -11.3041907951> 
   look_at <0, 0, 0> 
}

light_source {
    <50000, 1, 0> 
    color rgb <1.0, 1.0, 1.0>
    looks_like {
        sphere {
            <50000, 1, 0> , 2000
            pigment {
                color rgb <1.0, 0.7, 0.5>
            }
            finish {
                ambient 1.0
            }
        }
    }
}

union {
    sphere { 
        <0, 0, 0> 6.378
        texture {
            pigment {
                image_map {
                    gif "earth.gif"
                    map_type 1  
                }
            }
        } 
        scale<1.03,1,1.03> 
        rotate <12.5, 0, 0>
    }                     
    
    disc { 
        <0, 0, 0>   // center position 
        y,          // normal vector 
        14,         // outer radius 
        12          // optional hole radius 
        pigment {color red 0.59 green 0.78 blue 0.99 filter 0.6} 
        finish {ambient 0 diffuse 0.4} 
    }   
    
    disc { 
        <0, 0, 0> 
        y,  
        15, 
        14 
        pigment {color red 0.59 green 0.78 blue 0.99 filter 0.6} 
        finish {ambient 0 diffuse 0.8} 
    }    
    
    disc { 
        <0, 0, 0> 
        y, 
        16,
        15.5  
        pigment {color red 0.59 green 0.78 blue 0.99 filter 0.6} 
        finish {ambient 0 diffuse 0.2} 
    }  
    
    
    rotate <10,45,0>     
    translate <0,0,0> 
}

sphere { 
    <0,0,0> 1.722
    texture { 
        pigment {
            image_map {
                gif "moon.gif"
                map_type 1  // Esfera
            }
        } 
    }  
    scale<1.07,1,1.07>
    translate <128,0,0>  
}
