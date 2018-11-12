#include "colors.inc"  
#include "textures.inc"

global_settings {
    noise_generator 3
    max_trace_level 4
}        

camera { 
   location <100, 1, 10> //cam
   look_at <0, 20, 0> //cam
}

light_source {
    <5000, 5000, 4000> 
    color rgb <1.0, 1.0, 1.0>
}  

light_source {
    <0, 2, 0> 
    color rgb <1.0, 0.35, 0.0> spotlight
    radius 25
    falloff 55
    point_at <0,0,0>    
}

union {
    cone {
        <0, -5, 0>, 15
        <0, 1, 0>, 5
        pigment {
            color rgb <0.75, 0.75, 0.75>
        }
    }

    box {
        <-0.5, 1, -1.5>, <0.5, 3.5, -1>
        pigment{
            color rgb <0.45, 0.1, 0.1>
        }
    }

    box {
        <-0.5, 1, 1.5>, <0.5, 3.5, 1>
        pigment{
            color rgb <0.45, 0.1, 0.1>     
            
        }  
    }
}


box {
    <-0.25, -0.125, 0>, <0.25, 0.125, 0.5>
    pigment{
        color rgb <0.25, 0.25, 0.25>
    }  
    material {
        texture { New_Brass }
        scale 1      
    }  
    rotate <0, 0, 0> //box1 
    translate <0, 3.375, -1.00> //box1
}

box { 
    <-0.25, -0.125, 0>, <0.25, 0.125, -0.5>
    pigment{
        color rgb <0.25, 0.25, 0.25>
    }  
    material {
        texture { New_Brass }
        scale 1      
    }  
    rotate <0, 0, 0> //box2
    translate <0, 3.375, 1.00> //box2
}


union {
    cylinder {
        <0, -0.75, 0>, <0, 0.75, 0>, 0.5
        pigment {
            color rgb <0.5, 0.5, 0.5>
        } 
          
        material {
            texture { Bronze_Metal }
            scale 1    
        } 
    }
         
    difference {  
        cone {
            <0, -1.25, 0>, 0.45
            <0, -0.75, 0>, 0.25
            pigment {
                color rgb <0.1, 0.1, 0.1>
            } 
        }     
        
        sphere {
             <0, -1.25, 0>, 0.35
        }
    }

    cone {
        <0, 0.75, 0>, 0.5
        <0, 1.25, 0>, 0
        pigment {
            color rgb <0.5, 0.5, 0.5>
        }
        
        material {
            texture { Bronze_Metal }
            scale 1         
        }  
    }
    
    translate <0, 3, 0> //rocket
}     

sky_sphere {
    pigment {     
        
      gradient y
      color_map {
        [0.5 color rgb <0.2, 0.2, 1.0>]
        [1.0 color rgb <0.4, 0.4, 1.0>]
      }
      scale 2
      translate -1
    }
}                  

plane {    
     y, 0
     pigment {
         color rgb <0.25, 1.0, 0.25>
     }
} //plane