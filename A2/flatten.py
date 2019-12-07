
inlist = [[[1],2],[[[[[3]]]]],[4,[5],[[6]]]]

def thunk(i):
	print(i)

def flatten(lst):
	for i in lst:
		if type(i) == list:
			yield from flatten(i)
		else:
			yield i

#-------Test 1
for x in flatten(inlist):
	print(x)

#-------Test 2
outlist = [ x for x in flatten(inlist)]
print(outlist)

#-------Test 3
list1 = list(flatten(inlist))
print(list1)

n = 15
arr = [0]*n
print(len(arr))
arr[0] = 0
arr[1] = 1
arr[2] = 1
def fib(n, arr):
	if n == 0:
		# arr[0] = 0
		return arr[0]
	if n == 1:
		# arr[1] = 1
		return arr[1]
	if n == 2:
		# arr[2] = 1
		return arr[2]
	if arr[n] > 0:
		return arr[n]
	else:
		arr[n] = fib(n-1, arr)+fib(n-2, arr)
		return arr[n]
print(fib(n,arr))
