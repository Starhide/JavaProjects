import os
import re
import math
import subprocess
import random
from collections import OrderedDict
		
MAX_FRAMES = 1080

# Particle class has velocity, lifetime, visuals, and a updateFn which is called when update is called
class Particle():
	end_size = 0.25
	totalLife = 16

	# kwargs is key based strings arguments that are appended to the code of the particle
	def __init__(self, size, position, rotation, velocity, angVelocity, updateFn, **kwargs):
		self.size = size
		self.lifetime = Particle.totalLife
		self.position = position
		self.rotation = rotation
		self.velocity = velocity
		self.angVelocity = angVelocity
		self.stuff = kwargs
		self.updateFn = updateFn
	
	def update(self):
		self.lifetime -= 1

		self.position["x"] += self.velocity["x"]
		self.position["y"] += self.velocity["y"]
		self.position["z"] += self.velocity["z"]

		self.rotation["x"] += self.angVelocity["x"]
		self.rotation["y"] += self.angVelocity["y"]
		self.rotation["z"] += self.angVelocity["z"]

		self.updateFn(self)

		if self.lifetime == 0:
			return False
		return True
	
	def accelerate(self, x, y, z):
		self.angVelocity["x"] += x
		self.angVelocity["y"] += y
		self.angVelocity["z"] += z

	def getCode(self):
		pos = "<{x}, {y}, {z}>".format(**self.position)
		rot = "<{x}, {y}, {z}>".format(**self.rotation)
		code = 'sphere {{ {} {} rotate {}'.format(pos, self.size, rot)
		for k,v in self.stuff:
			code = code + v
		return code + ' }}\n'

# Generates a povray image
def generate_image(engine, pov_file, img_file, h, w):
	cmd = '"%s" /EXIT +I%s Output_File_Name="%s" -D -V +A  +H%s +W%s' % (engine,pov_file, img_file, h, w, )
	subprocess.call(cmd, shell=True)

# moves stuff around and writes to povray temp file
def move_objects(filename, frame, particles, rpos):
	file = open(filename)
	str = file.read()
	file.close()
	
	if frame < 16:
		str = re.sub(r'color rgb (<-?[\.\d]+, -?[\.\d]+, -?[\.\d]+>) spotlight', 'color rgb <{}, {}, 0.0> spotlight'.format(0.0625*frame, 0.022*frame), str)
	elif frame >= 192:
		str = re.sub(r'rotate (<-?[\.\d]+, -?[\.\d]+, -?[\.\d]+>) //box1', r'rotate <{}, 0, 0> //box1'.format(min(48, (frame-192)*2)), str)
		str = re.sub(r'rotate (<-?[\.\d]+, -?[\.\d]+, -?[\.\d]+>) //box2', r'rotate <{}, 0, 0> //box1'.format(max(-48, -(frame-192)*2)), str)

	
	str = re.sub(r'translate <-?[\.\d]+, -?[\.\d]+, -?[\.\d]+> //rocket', r'translate <{}, {}, {}> //rocket'.format(rpos[0], rpos[1], rpos[2]), str)
	campos = (rpos[0]+10, rpos[1], rpos[2])
	look_at = rpos
	if frame < 96:
		campos = (1.3, 1.25, -1.0)
		look_at = (0, 1.75, 0)
	elif frame < 192:
		campos = (4, 3.1, 4)
		look_at = (0, 2, 0)
	elif frame < 312:
		campos = (12, 7, -8)
		look_at = (0, 4, 0)
	elif frame < 456:
		campos = (1.25+rpos[0], 7+rpos[1], -0.5+rpos[2])
	elif frame < 648:
		campos = (100, 1, 10)
	else:
		str = re.sub(r'plane[\s\S]*//plane', r'', str)
		t = 2*3.14159*(frame % 144)/144
		campos = [rpos[0]+8*math.sin(t), rpos[1]+1*math.cos(t)-0.5, rpos[2]+8*math.cos(t)]

	str = re.sub(r'location <-?[\.\d]+, -?[\.\d]+, -?[\.\d]+> //cam', r'location <%s, %s, %s> //cam' % (campos[0], campos[1], campos[2]), str)
	str = re.sub(r'look_at <-?[\.\d]+, -?[\.\d]+, -?[\.\d]+> //cam', r'look_at <%s, %s, %s> //cam' % (look_at[0], look_at[1], look_at[2]), str)
	
	a = min(1.0, max(0.0, 1.0 - 1.0/240.0 * (frame-648)))
	b =	min(0.2, max(0.0, 0.2 - 0.2/240.0 * (frame-648)))
	c = min(0.4, max(0.0, 0.4 - 0.4/240.0 * (frame-648)))
	str = re.sub(r'\[0.5 color rgb <[\.\d]+, [\.\d]+, [\.\d]+>\]', '[0.5 color rgb <{}, {}, {}>]'.format(b,b,a), str)	
	str = re.sub(r'\[1.0 color rgb <[\.\d]+, [\.\d]+, [\.\d]+>\]', '[1.0 color rgb <{}, {}, {}>]'.format(c,c,a), str)	

	for p in particles:
		str += p.getCode()

	fout = open('temp.pov', 'w')
	fout.write(str)
	fout.close()

# Returns a new particle with some preset arguments
def new_particle(p, fn):
	return Particle(0.05, 
					{'x': p[0], 'y': p[1]-0.9, 'z': p[2]}, 
					{'x': 0, 'y': 0, 'z': 0}, 
					{'x': 0.05*random.random()-0.025, 'y': -(0.1+0.02*random.random()), 'z': 0.05*random.random()-0.025}, 
					{'x': 0, 'y': 0, 'z': 0}, fn
					visuals="pigment { color rgbf <0.75, 0.375, 0.0, 1.0> } no_shadow")

def main():
	# Changes color and alpha based on precentage of life left
	# Only updateFn function used figured I leave it out
	def lifetime_alpha(self):
		alpha = float(self.lifetime) / Particle.totalLife

		self.size = (Particle.end_size)*(1-alpha) + 0.05

		r = max(min(1.0, 1.85 - alpha*1.25), 0)
		g = max(min(1.0, 1.05 - alpha*1.25), 0)
		b = max(min(1.0, 0.55 - alpha*1.25), 0)
		self.stuff['visuals'] = re.sub(r'color rgbf <([\.\d]+), ([\.\d]+), ([\.\d]+), ([\.\d]+)>', r'color rgbf <{}, {}, {}, {}>'.format(r, g, b, alpha), self.stuff['visuals'])

	# params
	pov_engine = 'C:\\Program Files\\POV-Ray\\v3.7\\bin\\pvengine64.exe'
	pov_file = 'system.pov'
	h = 1080
	w = 1440

	
	particles = []
	rocket_pos = [0,3,0]

	for i in range(MAX_FRAMES):
		for x in range(10): 			# Create 10 particles every frame
			p = new_particle(rocket_pos, lifetime_alpha)
			if i < 48:
				p.velocity['y'] = -(float(i)/48)*(0.1+0.02*random.random())-.035
				Particle.end_size = float(i)/200
			particles.append(p)

		if i > 192:			#Rocket propulsion
			rocket_pos[1] += min(0.25, 0.0004*(i-192)+.01)
			Particle.totalLife = min(16 + (0.5)*(i-192), 48)
			Particle.end_size = min(0.48, 0.24 + (0.24/96)*(i-192))

		print(i)
		particles = [p for p in particles if p.update()] #Update all particles
		move_objects(pov_file, i, particles, rocket_pos)
		img_file = 'C:\\Users\\blake\\Programming\\cs360\\Lab 2\\images\\img%s.png' % (str(i).zfill(4))
		generate_image(pov_engine, 'temp.pov', img_file, h, w)
	


if __name__ == '__main__':
	main()