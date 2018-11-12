import os
import re
import math
import subprocess

def generate_image(engine, pov_file, img_file, h, w):
	cmd = '"%s" /EXIT +I%s Output_File_Name="%s" -D -V +A  +H%s +W%s' % (engine,pov_file, img_file, h, w, )
	print cmd
	subprocess.call(cmd, shell=True)

def move_objects(filename, x, y, z):
	file = open(filename)
	str = file.read()
	file.close()
	
	str = re.sub(r'(camera[\s\S]*location )(<-?\d+, -?\d+, -?\d+>)', r'\1 <%s, %s, %s>' % (x, z, y), str)
	#match = re.search(r'camera[\s\S]*location (<-?\d+, -?\d+, -?\d+>)', str)
	#if match:
	#	print 'Found'
	#else:
	#	print 'Nope'
		
	fout = open('temp.pov', 'w')
	fout.write(str)
	fout.close()
	
	
def main():
	pov_engine = 'C:\\Program Files\\POV-Ray\\v3.7\\bin\\pvengine64.exe'
	pov_file = 'system.pov'
	
	h = 1080
	w = 1920
	frames = 200
	r = 180
	for i in range(frames):
		x = r * math.cos(float(i)/frames*4*3.14159)
		y = r * math.sin(float(i)/frames*4*3.14159)
		move_objects(pov_file, x, y, 15)
		img_file = 'C:\\Users\\blake\\Programming\\cs360\\Lab 2\\images\\img%s.png' % (str(i).zfill(4))
		generate_image(pov_engine, 'temp.pov', img_file, h, w)
	
if __name__ == '__main__':
	main()