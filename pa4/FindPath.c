#include <stdlib.h>
#include <string.h>
#include <stdio.h>

#include "Graph.h"
#define MAXIMUM_LENGTH 165

int main (int argc, char* argv[]){
  // temporary char array to hold string
  char line[MAXIMUM_LENGTH];
  // Command line argument checker
  if(argc != 3) {
    printf("Invalid number of ins");
    exit(1);
  }

  FILE* in = fopen(argv[1], "r");
  FILE* out = fopen(argv[2], "w");

  if(in == NULL){
    printf("File %s Can not be Opened Open\n", argv[1]);
    return 1;
  } else if (out == NULL){
    printf("File %s Can not be Opened\n", argv[2]);
    return 1;
  }

  // read each line and print tokens
  fgets(line, MAXIMUM_LENGTH, in);
  int vertex = 0;
  sscanf(line, "%d", &vertex);

  // creating the graph
  Graph G = newGraph(vertex);
  while( fgets(line, MAXIMUM_LENGTH, in) != NULL) {
    int v1 = 0;
    int v2 = 0;
    sscanf(line, "%d %d", &v1, &v2);
    if(v1 == 0 && v2 == 0) break;
    addEdge(G, v1, v2);
  }
  printGraph(out, G);
  List L;
  // this eliminates the new line
  int start = 0;
  while(fgets(line, MAXIMUM_LENGTH, in) != NULL) {
    int v1 = 0;
    int v2 = 0;
    start ++;
    sscanf(line, "%d %d", &v1, &v2);

    if(v1 == 0 && v2 == 0) break;
    if(start != 1) fprintf(out, "\n");
    fprintf(out, "\n");
    BFS(G, v1);
    int dist = getDist(G, v2);
    fprintf(out, "The distance from %d to %d is ", v1, v2);
    if(dist == -255) {
      fprintf(out, "infinity\n");
    } else {
      fprintf(out, "%d\n", dist);
    }
    L = newList();
    getPath(L, G, v2);
    if(front(L) == -254){
      fprintf(out, "No %d-%d path exists", v1, v2);
    } else {
      fprintf(out, "A shortest %d-%d path is: ", v1, v2);
      printList(out, L);
    }


    freeList(&L);
  }

  fprintf(out, "\n");
  freeGraph(&G);
  fclose(in);
  fclose(out);
  return(0);
}
