import numpy as np
import secrets
import matplotlib.pyplot as plt
from cryptography.hazmat.primitives.asymmetric import rsa
import hashlib
def initRandom(n):
    return [secrets.randbelow(110+0) for _ in range(n)] 

def initRandomNum():
    return secrets.randbelow(16)

def j(n,x):
    rest = n - x
    return rest 




def saveTxt(name, n):
    with open(name, "w") as f:
        f.write(" ".join(str(val) for val in n))


def convertInput(userid):
    userid = fuckUpUserID
    max = 16
    ivMatrix = []
    iv = initRandom(max)
    ## saving random vector for decrypting
    saveTxt("init vector", iv)

    for i in range(len(iv)):
        rest = j(max, i)
        restRand = initRandom(rest)
        saveTxt("restRand vector", restRand)
        restIndex = initRandom(i)
        saveTxt("restIndex vector", restIndex)
        ivMatrix.append(restRand + restIndex)

    matrix = np.vstack(ivMatrix)
    print("Matrix before:",matrix)
    
    for i in range(len(iv)):
        rand_i = initRandomNum()
        saveTxt("random index number", rand_i)
        matrix[[i,rand_i]] = matrix[[rand_i, i]]
    matrix = matrix + userid
    matrix = np.rot90(matrix, k=userid)
    matrix = np.fliplr(matrix)
    matrix = np.roll(matrix, shift=1, axis=userid)
    matrix[:,1] = matrix[:,1][::-1]
    print("Matrix after:",matrix)

def MEGABIGMODULUS(bits=2048):
    return secrets.randbits(bits)






def fuckUpUserID(userID):
    ## mega stort modulus
    n = MEGABIGMODULUS()
    # mega stor exponent
    e = secrets.randbits(256)
    ## fuck it up 
    cUserID = pow(userID, e, n)
    ## gör till bytes
    byte_id = str(cUserID).encode()
    ## fuck it up some more med hashing
    hash_bytes = hashlib.sha256(byte_id).digest()
    ## skapa ett random seed skapat från userid (4 bytes = 32 bits)
    seed = int.from_bytes(hash_bytes[:4], "big")
    ## skapa seed
    rng = np.random.RandomState(seed)  
    n_points = 500
    print("RNGGGGGGGGGGGGGGGGGGGGGGG",rng)
    ## skapa en cool våg av userid
    random_wave = rng.uniform(-1, 1, n_points)
    print("WAAAAAAAAAAAAVEEEEEEEEEEEEEE", random_wave)

    x = np.arange(len(random_wave))
    plt.figure(figsize=(10, 4))
    plt.plot(x, random_wave, color='blue', linewidth=1)
    plt.title(f"Random Wave for userID={userID}")
    plt.xlabel("Sample Index")
    plt.ylabel("Amplitude")
    plt.grid(True)
    plt.show()
    return random_wave

fuckUpUserID(19)