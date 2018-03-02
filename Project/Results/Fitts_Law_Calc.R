# Index of Difficulty Function
ID = function(x0, y0, x1, y1, w) {
  a = sqrt((x1-x0)^2 + (y1-y0)^2)
  log2((a/w) + 1)
}

# Check that the function works as expected
#ID(0, 0, 1, 1, 1)
#log2(sqrt(2) + 1)

# Movement Function
MT = function(x) {50 + 150*x}

# Matrix of estimated start/end button coordinates and target widths
# Empty column is for movement time 
data = c(4.5, 2.75, 1.375, 1.5, 1.375, 0,
         1.375, 1.5, 3.5, 4.25, 1.5, 0,
         3.5, 4.25, 3.5, 3.75, 1.5, 0,
         3.5, 3.75, 3.5, 3.75, 1.5, 0,
         3.5, 3.75, 3.5, 2.75, 1.5, 0,
         3.5, 2.75, 3.5, 3.25, 1.5, 0,
         3.5, 3.25, 3.5, 3.25, 1.5, 0,
         3.5, 3.25, 4.5, 2.5, 1.375, 0)
m = matrix(data, nr = 8, nc = 6, byrow = TRUE)
colnames(m) = c("x0", "y0", "x1", "y1", "w", "t")

# View matrix
m

# Calculate Fitt's Law time estimate for each task
for(i in 1:8) {
  m[i, "t"] = MT(ID(m[i, "x0"], m[i, "y0"], m[i, "x1"], m[i, "y1"], m[i, "w"]))
}

# Sum of time estimates
sum(m[,"t"])
