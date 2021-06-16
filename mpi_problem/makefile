#CC    = mpifccpx
#CPP   = mpiFCCpx
#CC    = FCpx
#CPP   = FCCpx
#CC    = gcc
#CPP   = g++

# when compiling by clang++, export OMPI_CXX in advance
# export OMPI_CXX=clang++

CC    = mpicc
CPP   = mpic++

TARGET  = ./gcc_mop_mpi
SRCDIR  = readOptions/ \
          VectorTools/ \
          mops/ 

#MPI_DIR = /usr/include/mpich-x86_64
#MPI_LIB = /usr/lib64/mpich/lib

HDRDIR     = $(SRCDIR) $(MPI_DIR)
ODIR       = objs

#FLAGS       = -O3 -Kfast -fopenmp -Xg
FLAGS       = -O3

#Include direcotory flags
FLAGS += $(patsubst %,-I%, $(HDRDIR))

# Defined symbols flags, -D options
#VARIABLES   =
#FLAGS += $(patsubst %,-D%, $(VARIABLES))

# Libraries flags for linking, -l options
LIBS        = boost_program_options \
              boost_random \
              boost_regex
#			  mpichcxx \
#			  mpich

LIBSFLAGS = $(patsubst %,-l%, $(LIBS))

# Libraries flags for linking, -L options
 LIBSDIR     = $(MPI_LIB)
 LIBSDIRFLAGS = $(patsubst %,-L%, $(LIBSDIR))

vpath %.cpp $(SRCDIR)
vpath %.c $(SRCDIR)
vpath %.h $(HDRDIR)

# Get a list of all the *.c and *.cpp (path included)
SOURCES = $(foreach dir,$(SRCDIR),$(wildcard $(dir)/*.cpp $(dir)/*.c)) \
		  main_mpi.cpp \
		  MasterSlaveMPI.cpp 

# Remove the path so that only the names of the sources are kept.
SOURCESNAMES=$(notdir $(SOURCES))

# To get the list of generated objects change the file extension to *.o and append the directory of the object files.
OBJECTS    = $(patsubst %, $(ODIR)/%.o, $(basename $(SOURCESNAMES)))

all: $(TARGET)

$(TARGET): $(OBJECTS)
	@echo ' '
	@echo 'Building target: $@'
	@echo ' '
	$(CPP) $(LIBSFLAGS) $(LIBSDIRFLAGS) $(FLAGS) -o $@ $^
	@echo ' '
	@echo 'Finished building target: $@'
	@echo ' '
#	make --no-print-directory post-build

$(ODIR)/%.o: %.cpp
	$(CPP) $(VARFLAGS) $(IFLAGS) $(FLAGS) -c -o $@ $<

$(ODIR)/%.o: %.c
	$(CC) $(VARFLAGS) $(IFLAGS) $(FLAGS) -c -o $@ $<


clean:
	-rm -f $(OBJECTS) $(TARGET)

print:
	-@echo $(IFLAGS)

# post-build:
# 	-cp $(TARGET) /group/gc15/c15068/destiny/work/
# 	-@echo ' '


.PHONY : all release clean print
.SECONDARY: post-build
