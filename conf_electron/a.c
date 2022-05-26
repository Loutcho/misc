#include <stdio.h>
#include <math.h>

#define PI 3.141592

static char* color[] = { "#ffffb2", "#fed976", "#feb24c", "#fd8d3c", "#fc4e2a", "#e31a1c", "#b10026" }; 

static char letter[] = { 's', 'p', 'd', 'f' };


static void header()
{
	printf("<?xml version='1.0' standalone='no'?>\n");
	printf("<svg width='900' height='900' version='1.1' xmlns='http://www.w3.org/2000/svg'>\n");
}

static void footer()
{
	printf("</svg>\n");
}

static void circle(double cx, double cy, double r, char *stroke, char *fill)
{
	printf("<circle cx='%f' cy='%f' r='%f' stroke='%s' stroke-width='3' fill='%s' />\n", cx, cy, r, stroke, fill);
}

static void line(double x1, double y1, double x2, double y2)
{
	printf("<line x1='%f' y1='%f' x2='%f' y2='%f' style='stroke:black;stroke-width:3' />\n", x1, y1, x2, y2);
}

static void rect(double x, double y, double width, double height, char *fill)
{
	printf("<rect x='%f' y='%f' width='%f' height='%f' stroke='black' stroke-width='3' fill='%s' />\n", x, y, width, height, fill);
}

static void text(double x, double y, char *s)
{
	printf("<text x='%f' y='%f' fill='black'>%s</text>\n", x, y, s);
}

static int p(int n, int l)
{
	return 10 * (n * n + 3 * n + 2 * l + 2);
}

int main(int argc, char *argv[])
{
	int n, l, ml, nl, i;
	double cx, cy, r;

	header();

	cx = 0.0;
	cy = 100.0;

	for (n = 7; n >= 1; n --)
	{
		for (l = n - 1; l >= 0; l --)
		{
			r = p(n, l);
			circle(cx, cy, r, "black", color[l]);
			for (ml = 1; ml <= 2 * l; ml ++)
			{
				double theta;
				double R;
				double x1, y1, x2, y2;
				theta = (PI * ml) / (2.0 * (2 * l + 1));
				R = p(n, l - 1);
				x1 = cx + r * cos(theta);
				y1 = cy + r * sin(theta);
				x2 = cx + R * cos(theta);
				y2 = cy + R * sin(theta);
				line(x1, y1, x2, y2);
			}
			if (l <= 3)
			{
				char tmp[10 + 1];
				sprintf(tmp, "%d%c", n, letter[l]);
				text(cx, cy + r - 5, tmp);
			}
		}
		r = p(n, -1);
		circle(cx, cy, r, "black", "white");
	}

	rect(0, 0, 1000, 100, "white");

	footer();
}


/*

<text font-size="10" x="25" y="12" fill="green">1</text> <!-- 1s -->
<text font-size="10" x="85" y="12" fill="green">2</text> <!-- 2s -->
<text font-size="10" x="105" y="12" fill="green">3</text> <!-- 2p -->
<text font-size="10" x="165" y="12" fill="green">4</text> <!-- 3s -->
<text font-size="10" x="185" y="12" fill="green">5</text> <!-- 3p -->
<text font-size="10" x="265" y="12" fill="green">6</text> <!-- 4s -->
<text font-size="10" x="205" y="12" fill="green">7</text> <!-- 3d -->
<text font-size="10" x="285" y="12" fill="green">8</text> <!-- 4p -->
<text font-size="10" x="385" y="12" fill="green">9</text> <!-- 5s -->
<text font-size="10" x="305" y="12" fill="green">10</text> <!-- 4d -->
<text font-size="10" x="405" y="12" fill="green">11</text> <!-- 5p -->
<text font-size="10" x="525" y="12" fill="green">12</text> <!-- 6s -->
<text font-size="10" x="325" y="12" fill="green">13</text> <!-- 4f -->
<text font-size="10" x="425" y="12" fill="green">14</text> <!-- 5d -->
<text font-size="10" x="545" y="12" fill="green">15</text> <!-- 6p -->
<text font-size="10" x="685" y="12" fill="green">16</text> <!-- 7s -->
<text font-size="10" x="445" y="12" fill="green">17</text> <!-- 5f -->
<text font-size="10" x="565" y="12" fill="green">18</text> <!-- 6d -->
<text font-size="10" x="705" y="12" fill="green">19</text> <!-- 7p -->
*/
